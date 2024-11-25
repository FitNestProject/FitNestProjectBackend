package org.ilisi.event.clients;


import org.ilisi.event.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthServiceFeignClient {

    @GetMapping("/user/getUserById/{userId}")
    User getUserById(@PathVariable("userId") Long userId);
}

