package org.ilisi.auth_service.clients;

import org.ilisi.auth_service.dto.EventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "event-service")
public interface EventFeignClient {

   @GetMapping("/api/events/user")
   List<EventDto> getEventsByUserId(@RequestParam("userId") Long userId);
}
