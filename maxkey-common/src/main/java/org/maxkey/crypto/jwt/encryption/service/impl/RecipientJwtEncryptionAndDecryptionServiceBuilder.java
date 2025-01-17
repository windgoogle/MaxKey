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
 

/**
 * 
 */
package org.maxkey.crypto.jwt.encryption.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import org.apache.log4j.Logger;
import org.maxkey.crypto.jose.keystore.JWKSetKeyStore;
import org.maxkey.crypto.jwt.encryption.service.JwtEncryptionAndDecryptionService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;

/**
 * @author Crystal.Sea
 *
 */
public class RecipientJwtEncryptionAndDecryptionServiceBuilder {
	final static Logger logger = Logger.getLogger(RecipientJwtEncryptionAndDecryptionServiceBuilder.class);
	
	//private HttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
	//private HttpComponentsClientHttpRequestFactory httpFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
	//private RestTemplate restTemplate = new RestTemplate(httpFactory);
	
	/**
	 * 
	 */
	public RecipientJwtEncryptionAndDecryptionServiceBuilder() {
		
	}
	
	public JwtEncryptionAndDecryptionService serviceBuilder(String jwksUri){
		
		logger.debug("jwksUri : "+jwksUri);
		
		String jsonString ="";//= restTemplate.getForObject(jwksUri, String.class);
		
		logger.debug("jwks json String : "+jsonString);
		JwtEncryptionAndDecryptionService recipientJwtEncryptionAndDecryptionService;
		try {
			JWKSet jwkSet = JWKSet.parse(jsonString);

			JWKSetKeyStore keyStore = new JWKSetKeyStore(jwkSet);
			recipientJwtEncryptionAndDecryptionService = new DefaultJwtEncryptionAndDecryptionService(keyStore);
			
			return recipientJwtEncryptionAndDecryptionService;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (JOSEException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
