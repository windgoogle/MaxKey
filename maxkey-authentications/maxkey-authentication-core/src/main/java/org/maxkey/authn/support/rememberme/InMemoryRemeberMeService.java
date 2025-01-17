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
 

package org.maxkey.authn.support.rememberme;

import java.util.concurrent.TimeUnit;

import org.maxkey.constants.ConstantsTimeInterval;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class InMemoryRemeberMeService   extends AbstractRemeberMeService {

    protected static final Cache<String, RemeberMe> remeberMeStore = 
            Caffeine.newBuilder()
                .expireAfterWrite(ConstantsTimeInterval.TWO_WEEK, TimeUnit.MINUTES)
                .build();
    
    @Override
    public void save(RemeberMe remeberMe) {
        remeberMeStore.put(remeberMe.getUsername(), remeberMe);
    }

    @Override
    public void update(RemeberMe remeberMe) {
        remeberMeStore.put(remeberMe.getUsername(), remeberMe);
    }

    @Override
    public RemeberMe read(RemeberMe remeberMe) {
        return remeberMeStore.getIfPresent(remeberMe.getUsername());
    }

    @Override
    public void remove(String username) {
        remeberMeStore.invalidate(username);
    }

}
