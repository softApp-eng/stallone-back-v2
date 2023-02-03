package far.insp.sirhat.presentation.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/run")
    public ResponseEntity<?> me(){
        return ResponseEntity.ok("server is running");
    }
}
