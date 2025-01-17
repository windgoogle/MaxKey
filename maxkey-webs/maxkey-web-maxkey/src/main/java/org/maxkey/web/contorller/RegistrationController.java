/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.maxkey.web.contorller;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstantsStatus;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.Registration;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.RegistrationService;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.DateUtils;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/registration"})
public class RegistrationController {
	private static Logger _logger = LoggerFactory.getLogger(RegistrationController.class);
	
	@Autowired
	RegistrationService registrationService;
	
	@Autowired 
  	@Qualifier("applicationConfig")
  	protected ApplicationConfig applicationConfig;
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@RequestMapping(value={"/forward"})
	public ModelAndView forward() {
		_logger.debug("Registration  /registration/register.");
		return  new ModelAndView("registration/register");
	}
	
	//邮件验证注册
	@RequestMapping(value={"/register"})
	public ModelAndView reg(@ModelAttribute("registration") Registration registration) {
		_logger.debug("Registration  /registration/register.");
		_logger.debug(""+registration);
		ModelAndView modelAndView= new ModelAndView("registration/registered");
		
		UserInfo userInfo =registrationService.queryUserInfoByEmail(registration.getWorkEmail());
		
		if(userInfo!=null){
			modelAndView.addObject("registered", 1);
			return modelAndView;
		}
		
		registration.setId(registration.generateId());
		registrationService.insert(registration);
		HtmlEmail email = new HtmlEmail();
		  
		  try {
			email.setHostName(applicationConfig.getEmailConfig().getSmtpHost());
			email.setSmtpPort(applicationConfig.getEmailConfig().getPort());
			email.setAuthenticator(new DefaultAuthenticator(
							applicationConfig.getEmailConfig().getUsername(), 
							applicationConfig.getEmailConfig().getPassword()
						));
			
			email.addTo(registration.getWorkEmail(), registration.getLastName()+registration.getFirstName());
			email.setFrom(applicationConfig.getEmailConfig().getSender(), "MaxKey");
			email.setSubject("MaxKey Identity & Access Registration activate Email .");
			  
			String activateUrl=WebContext.getHttpContextPath()+"/registration/forward/activate/"+registration.getId();
			
			
			// set the html message
			String emailText="<html>";
			 			emailText+="<a href='"+activateUrl+"'>activate</a><br>";
			 			emailText+=" or copy "+activateUrl+" to brower.";
			 	   emailText+="</html>";
			email.setHtmlMsg(emailText);
			
			// set the alternative message
			email.setTextMsg("Your email client does not support HTML messages");
			
			// send the email
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
		  modelAndView.addObject("registered", 0); 
		return  modelAndView;
	}
	
	@RequestMapping(value={"/forward/activate/{id}"})
	public ModelAndView confirm(@PathVariable("id") String id) {
		_logger.debug("Registration  /registration/forward/activate.");
		Registration registration=registrationService.get(id);
		ModelAndView mav=new ModelAndView("registration/activate");
		if(registration!=null){
			mav.addObject("model", registration);
		}
		
		return mav;
	}
	
	
	@RequestMapping(value={"/activate/{id}"})
	public ModelAndView setPassWord(@PathVariable("id") String id,
									@RequestParam String password,
									@RequestParam String confirmpassword) {
		_logger.debug("Registration  /registration/setpassword.");
		ModelAndView modelAndView=new ModelAndView("registration/activated");
		if(password.equals(confirmpassword)){
			Registration registration=registrationService.get(id);
			if(registration!=null){
				SqlSession  sqlSession  = SqlSessionUtils.getSqlSession(
									WebContext.getBean("sqlSessionFactory",SqlSessionFactory.class));
				sqlSession.commit(false);
				
				UserInfo userInfo=new UserInfo();
				userInfo.setUsername(registration.getWorkEmail());
				userInfo.setFamilyName(registration.getLastName());
				userInfo.setGivenName(registration.getFirstName());
				
				userInfo.setWorkPhoneNumber(registration.getWorkPhone());
				userInfo.setEmail(registration.getWorkEmail());
				userInfo.setStatus(ConstantsStatus.ACTIVE);
				String rawPassword=PasswordReciprocal.getInstance().rawPassword(userInfo.getUsername(), password);
				userInfo.setDecipherable(ReciprocalUtils.encode(rawPassword));
				
				password = passwordEncoder.encode(rawPassword );
				userInfo.setPassword(password);
				userInfo.setPasswordLastSetTime(DateUtils.format(new Date(), DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));
				userInfoService.insert(userInfo);

				registrationService.remove(id);
				sqlSession.commit(true);
				modelAndView.addObject("activate", 1);
			}else{
				modelAndView.addObject("activate", 2);
			}
		}else{
			modelAndView.addObject("activate", 0);
		}
		return  modelAndView;
	}
 	
	//直接注册
 	@RequestMapping(value={"/registeron"})
 	@ResponseBody
	public Message registeron(UserInfo userInfo,@RequestParam String emailMobile) throws ServletException, IOException {
 		if(StringUtils.isEmpty(emailMobile)) {
 			return new Message(WebContext.getI18nValue("register.emailMobile.error"),"1");
 		}
 		if(StringUtils.isValidEmail(emailMobile)) {
 			userInfo.setEmail(emailMobile);
 		}
 		if(StringUtils.isValidMobileNo(emailMobile)) {
 			userInfo.setMobile(emailMobile);
 		}
 		if(!(StringUtils.isValidEmail(emailMobile)||StringUtils.isValidMobileNo(emailMobile))) {
 			return new Message(WebContext.getI18nValue("register.emailMobile.error"),"1");
 		}
 		UserInfo temp=userInfoService.queryUserInfoByEmailMobile(emailMobile);
 		if(temp!=null) {
 			return new Message(WebContext.getI18nValue("register.emailMobile.exist"),"1");
 		}
 		
 		temp=userInfoService.loadByUsername(userInfo.getUsername());
 		if(temp!=null) {
 			return new Message(WebContext.getI18nValue("register.user.error"),"1");
 		}
 		userInfo.setStatus(ConstantsStatus.ACTIVE);
 		if(userInfoService.insert(userInfo)) {
 			return new Message(WebContext.getI18nValue("login.text.register.success"),"0");
 		}
 		return new Message(WebContext.getI18nValue("login.text.register.error"),"1");
 		
 	}

}
