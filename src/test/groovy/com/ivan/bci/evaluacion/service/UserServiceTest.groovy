import com.ivan.bci.evaluacion.model.Phone
import com.ivan.bci.evaluacion.model.User
import com.ivan.bci.evaluacion.model.UserRequest
import com.ivan.bci.evaluacion.repository.IUserRepository
import com.ivan.bci.evaluacion.repository.IPhoneRepository
import com.ivan.bci.evaluacion.service.JwtService
import com.ivan.bci.evaluacion.service.UserService
import spock.lang.Specification

class UserServiceTest extends Specification
{

    static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}\$"


    def "addUserTest"()
    {
        given:
        def userRepository = Mock(IUserRepository)
        def phoneRepository = Mock(IPhoneRepository)
        def jwtService = Mock(JwtService)
        def userService = new UserService(userRepository, phoneRepository, jwtService, PASSWORD_REGEX)

        def phone = Phone.builder()
                .cityCode(1)
                .countryCode(1)
                .number(1)
                .build()

        def userRequest = createRequest("email@email.com", "name", "password123", [phone])

        userRepository.findByEmail(_) >> null
        userRepository.save(_ as User) >> new User()
        phoneRepository.save(_ as Phone) >> phone
        jwtService.generateToken(_) >> "token"

        when:
        userService.addUser(userRequest)

        then:
        1 * userRepository.findByEmail("email@email.com")
        1 * userRepository.save(_ as User)
        1 * phoneRepository.save(_ as Phone)
        1 * jwtService.generateToken("email@email.com")
    }

    def "addUserMultiplePhonesTest"()
    {
        given:
        def userRepository = Mock(IUserRepository)
        def phoneRepository = Mock(IPhoneRepository)
        def jwtService = Mock(JwtService)
        def userService = new UserService(userRepository, phoneRepository, jwtService, PASSWORD_REGEX)

        def phones = [
                Phone.builder()
                        .cityCode(1)
                        .countryCode(1)
                        .number(1)
                        .build(),
                Phone.builder()
                        .cityCode(2)
                        .countryCode(2)
                        .number(2)
                        .build()
        ]

        def userRequest = createRequest("email@email.com", "name", "password123", phones)

        userRepository.findByEmail(_) >> null
        userRepository.save(_ as User) >> new User()
        phoneRepository.save(_ as Phone) >> new Phone()
        jwtService.generateToken(_) >> "token"

        when:
        userService.addUser(userRequest)

        then:
        1 * userRepository.findByEmail("email@email.com")
        1 * userRepository.save(_ as User)
        2 * phoneRepository.save(_ as Phone)
        1 * jwtService.generateToken("email@email.com")
    }

    def "addUserExistingEmailTest"()
    {
        given:
        def userRepository = Mock(IUserRepository)
        def phoneRepository = Mock(IPhoneRepository)
        def jwtService = Mock(JwtService)
        def userService = new UserService(userRepository, phoneRepository, jwtService, PASSWORD_REGEX)

        userRepository.findByEmail(_) >> new User()

        when:
        userService.addUser(createRequest("email@email.com", "name", "password123", []))

        then:
        def exception = thrown(Exception)
        exception.message == "El correo ya ha sido registrado"
    }

    def "addUserWrongEmailTest"()
    {
        given:
        def userRepository = Mock(IUserRepository)
        def phoneRepository = Mock(IPhoneRepository)
        def jwtService = Mock(JwtService)
        def userService = new UserService(userRepository, phoneRepository, jwtService, PASSWORD_REGEX)

        when:
        userService.addUser(createRequest("Wrong_Email", "name", "password123", []))

        then:
        def exception = thrown(Exception)
        exception.message == "El correo no tiene un formato válido"
    }

    def "addUserWrongPasswordTest"()
    {
        given:
        def userRepository = Mock(IUserRepository)
        def phoneRepository = Mock(IPhoneRepository)
        def jwtService = Mock(JwtService)
        def userService = new UserService(userRepository, phoneRepository, jwtService, PASSWORD_REGEX)

        when:
        userService.addUser(createRequest("email@email.com", "name", "a", []))

        then:
        def exception = thrown(Exception)
        exception.message == "La contraseña no cumple con los requerimientos"
    }

    private UserRequest createRequest(String email, String name, String password, List<Phone> phoneList)
    {
        return UserRequest.builder()
                .email(email)
                .name(name)
                .password(password)
                .phones(phoneList)
                .build()
    }
}