import com.ivan.bci.evaluacion.controller.UserController
import com.ivan.bci.evaluacion.model.UserRequest
import com.ivan.bci.evaluacion.service.UserService
import spock.lang.Specification

class UserControllerTest extends Specification
{

    def "addUserTest"() {
        given:
        UserService service = Mock(UserService)
        UserController userController = new UserController(service)
        UserRequest userRequest = UserRequest.builder()
                .email("email@email.com")
                .name("name")
                .password("password")
                .phones(Collections.emptyList())
                .build()

        when:
        userController.newUser(userRequest)

        then:
        1 * service.addUser(userRequest)
    }
}