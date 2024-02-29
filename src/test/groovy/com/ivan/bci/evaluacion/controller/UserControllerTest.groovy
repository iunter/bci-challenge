import com.ivan.bci.evaluacion.controller.UserController
import com.ivan.bci.evaluacion.dto.UserResponseDto
import com.ivan.bci.evaluacion.model.UserModel
import com.ivan.bci.evaluacion.dto.UserRequestDto
import com.ivan.bci.evaluacion.service.JwtService
import com.ivan.bci.evaluacion.service.UserService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class UserControllerTest extends Specification
{

    def "addUserTest"() {
        given:
        def userService = Stub(UserService)
        def jwtService = Stub(JwtService)

        def expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 30)
        def issuedAt = new Date()

        def userRequest = UserRequestDto.builder()
                .email("email@email.com")
                .name("name")
                .password("password")
                .phones(Collections.emptyList())
                .build()

        def user = UserModel.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .password(userRequest.getPassword())
                .phoneList(userRequest.getPhones())
                .build()

        def userResponse = UserResponseDto.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .phoneList(userRequest.getPhones())
                .isActive(true)
                .lastLogin(issuedAt)
                .build()

        userService.addUser(userRequest) >> user
        jwtService.extractExpiration(_) >> expirationDate
        jwtService.extractClaim(_, _) >> issuedAt

        def userController = new UserController(userService, jwtService)

        when:
        def response = userController.newUser(userRequest)

        then:
        response.getBody() == userResponse
        response.getStatusCode() == HttpStatus.OK
    }

    def "addUserErrorTest"() {
        given:
        def userService = Stub(UserService)
        def jwtService = Mock(JwtService)

        UserController userController = new UserController(userService, jwtService)
        UserRequestDto userRequest = UserRequestDto.builder()
                .email("email@email.com")
                .name("name")
                .password("password")
                .phones(Collections.emptyList())
                .build()

        userService.addUser(_) >> {throw new Exception("Test")}

        when:
        def response = userController.newUser(userRequest)

        then:
        response.getBody() == "{\"mensaje\": \"Test\""
        response.getStatusCode() == HttpStatus.BAD_REQUEST
    }
}