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
 

package org.maxkey.crypto;

import java.security.Key;
import java.util.Map;

import org.junit.Test;


public class RSAUtilsTest {

	@Test
	public void test() throws Exception {

		// ˽Կ���ܡ�����Կ����
		// ˽Կǩ����Կ��֤ǩ��
		Map<String, Object> key = RSAUtils.genKeyPair();
		String privateKey = RSAUtils.getPublicKey2Hex(key);
		String publicKey = RSAUtils.getPrivateKey2Hex(key);
		System.out.println("privateKey:" + privateKey);
		System.out.println("publicKey:" + publicKey);
		String signString = "my name is shiming";
		Key keyp = (Key) key.get(RSAUtils.PUBLIC_KEY);
		System.out.println("privateKey:" + Base64Utils.base64UrlEncode(keyp.getEncoded()));

		byte[] encodedData = RSAUtils.encryptByPrivateKey(signString.getBytes(), privateKey);
		System.out.println("���ܺ�\r\n" + new String(encodedData));
		System.out.println("���ܺ�B64��\r\n" + HexUtils.bytes2HexString(encodedData));
		byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
		String target = new String(decodedData);
		System.out.println("target:" + target);

	}

}
