import com.ivan.bci.evaluacion.controller.UserController
import com.ivan.bci.evaluacion.controller.UserControllerAdvice
import com.ivan.bci.evaluacion.dto.UserResponseDto
import com.ivan.bci.evaluacion.model.UserModel
import com.ivan.bci.evaluacion.dto.UserRequestDto
import com.ivan.bci.evaluacion.service.IJwtService
import com.ivan.bci.evaluacion.service.IUserService
import com.ivan.bci.evaluacion.service.impl.UserServiceImpl
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import spock.lang.Specification

@SpringBootTest(classes = UserControllerAdvice.class)
class UserControllerTest extends Specification
{

    def "addUserTest"() {
        given:
        def userService = Stub(IUserService)
        def jwtService = Stub(IJwtService)

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
        jwtService.isTokenExpired(_) >> true

        def userController = new UserController(userService, jwtService)

        when:
        def response = userController.newUser(userRequest)

        then:
        response.getBody() == userResponse
        response.getStatusCode() == HttpStatus.OK
    }

    def "addUserErrorTest"() {
        given:
        def userService = Stub(IUserService)
        def jwtService = Mock(IJwtService)

        UserController userController = new UserController(userService, jwtService)

        UserRequestDto userRequest = UserRequestDto.builder()
                .email("email@email.com")
                .name("name")
                .password("password")
                .phones(Collections.emptyList())
                .build()

        userService.addUser(_) >> {throw new UserServiceImpl.UserServiceException("Test")}

        when:
        def response = userController.newUser(userRequest)

        then:
        thrown UserServiceImpl.UserServiceException
    }
}