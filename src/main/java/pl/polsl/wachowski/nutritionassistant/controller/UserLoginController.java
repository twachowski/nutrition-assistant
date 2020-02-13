package pl.polsl.wachowski.nutritionassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.wachowski.nutritionassistant.dto.jwt.JwtTokenDTO;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserLoginDTO;
import pl.polsl.wachowski.nutritionassistant.security.JwtHelper;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user/login")
public class UserLoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @Autowired
    public UserLoginController(final AuthenticationManager authenticationManager, final JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokenDTO> login(@RequestBody @Valid final UserLoginDTO userLoginDTO) {
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        authenticationManager.authenticate(authenticationToken);
        final String jwtToken = jwtHelper.generateToken(userLoginDTO.getEmail());

        return ResponseEntity.ok(new JwtTokenDTO(jwtToken));
    }

}
