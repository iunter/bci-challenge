import com.ivan.bci.evaluacion.controller.UserController
import com.ivan.bci.evaluacion.model.User
import com.ivan.bci.evaluacion.model.UserRequest
import com.ivan.bci.evaluacion.service.UserService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class UserControllerTest extends Specification
{

    def "addUserTest"() {
        given:
        def service = Stub(UserService)

        def user = new User()

        def userController = new UserController(service)

        def userRequest = UserRequest.builder()
                .email("email@email.com")
                .name("name")
                .password("password")
                .phones(Collections.emptyList())
                .build()

        service.addUser(userRequest) >> user

        when:
        def response = userController.newUser(userRequest)

        then:
        response.getBody() == user
        response.getStatusCode() == HttpStatus.OK
    }

    def "addUserErrorTest"() {
        given:
        UserService service = Stub(UserService)
        UserController userController = new UserController(service)
        UserRequest userRequest = UserRequest.builder()
                .email("email@email.com")
                .name("name")
                .password("password")
                .phones(Collections.emptyList())
                .build()

        service.addUser(_) >> {throw new Exception("Test")}

        when:
        def response = userController.newUser(userRequest)

        then:
        response.getBody() == "{\"mensaje\": \"Test\""
        response.getStatusCode() == HttpStatus.BAD_REQUEST
    }
}