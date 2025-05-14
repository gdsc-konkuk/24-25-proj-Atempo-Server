package juton113.Atempo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import juton113.Atempo.service.SseService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "SSE Communication", description = "SSE Communication APIs")
@AllArgsConstructor
@RequestMapping("/api/v1/notifications")
@RestController
public class SseController {
    private final SseService sseService;

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Subscribe to SSE Connection",
            description = "Subscribes to the SSE notification stream. – [Admins, Verified Users Only]"
    )
    @PreAuthorize("hasAnyRole('ADMIN', VERIFIED)")
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        return sseService.subscribe(memberId);
    }

}
