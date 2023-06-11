package com.project.sistemaStock;

import com.project.sistemaStock.controller.UserController;
import com.project.sistemaStock.dto.UserDTO;
import com.project.sistemaStock.model.User;
import com.project.sistemaStock.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SistemaStockApplicationTests {

    @Test
    void contextLoads() {
    }

    @Mock
    private IUserRepository iUserRepository;

    @InjectMocks
    private UserController userController;


    @Test
    public void testListUsers_ReturnsUserListWithCorrectData() {
        IUserRepository userRepository = mock(IUserRepository.class);
        // Creación de usuarios de prueba
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(UUID.fromString(UUID.randomUUID().toString()));
        user1.setName("John");
        user1.setSurname("Doe");
        user1.setDni("123456789");
        user1.setEmail("john.doe@example.com");
        user1.setPhone("1234567890");

        User user2 = new User();
        user2.setId(UUID.fromString(UUID.randomUUID().toString()));
        user2.setName("Jane");
        user2.setSurname("Smith");
        user2.setDni("987654321");
        user2.setEmail("jane.smith@example.com");
        user2.setPhone("0987654321");

        users.add(user1);
        users.add(user2);

        // Configuración del mock del repositorio
        when(userRepository.findAll()).thenReturn(users);

        // Instancia del controlador
        UserController userController = new UserController(userRepository);

        // Llamada al método listUsers
        Map<String, Object> response = userController.listUsers();

        // Verificación de los resultados
        assertNotNull(response);
        assertTrue(response.containsKey("errors"));
        assertTrue(response.containsKey("data"));
        assertTrue(response.containsKey("count"));

        List<UserDTO> userDTOS = (List<UserDTO>) response.get("data");
        assertEquals(users.size(), userDTOS.size());

        UserDTO userDTO1 = userDTOS.get(0);
        assertEquals(user1.getId(), userDTO1.getId());
        assertEquals(user1.getName(), userDTO1.getName());
        assertEquals(user1.getSurname(), userDTO1.getSurname());
        assertEquals(user1.getDni(), userDTO1.getDni());
        assertEquals(user1.getEmail(), userDTO1.getEmail());
        assertEquals(user1.getPhone(), userDTO1.getPhone());

        UserDTO userDTO2 = userDTOS.get(1);
        assertEquals(user2.getId(), userDTO2.getId());
        assertEquals(user2.getName(), userDTO2.getName());
        assertEquals(user2.getSurname(), userDTO2.getSurname());
        assertEquals(user2.getDni(), userDTO2.getDni());
        assertEquals(user2.getEmail(), userDTO2.getEmail());
        assertEquals(user2.getPhone(), userDTO2.getPhone());

        verify(userRepository, times(1)).findAll();
    }


    @Test
    public void testGetById_UserExists_ReturnsSuccessMessage() {
        UUID userId = UUID.fromString(UUID.randomUUID().toString());
        User user = new User();
        user.setId(userId);
        user.setStatus(true);

        when(iUserRepository.findById(userId)).thenReturn(Optional.of(user));
        ResponseEntity<?> response = userController.getUserById(userId.toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testGetById_UserNotExists_ReturnsNotFoundMessage() {
        UUID userId = UUID.fromString(UUID.randomUUID().toString());

        when(iUserRepository.findById(userId)).thenReturn(Optional.empty());
        ResponseEntity<?> response = userController.getUserById(userId.toString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    public void testDeleteById_UserExists_ReturnsSuccessMessage() {
        UUID userId = UUID.fromString(UUID.randomUUID().toString());
        User user = new User();
        user.setId(userId);
        user.setStatus(true);

        when(iUserRepository.findById(userId)).thenReturn(Optional.of(user));
        ResponseEntity<?> response = userController.deleteById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());

        assertFalse(user.getStatus()); // Verificar que el estado del usuario se haya cambiado a false
        verify(iUserRepository, times(1)).save(user); // Verificar que se llamó al método save del repositorio
    }

    @Test
    public void testDeleteById_UserNotExists_ReturnsNotFoundMessage() {
        UUID userId = UUID.fromString(UUID.randomUUID().toString());

        when(iUserRepository.findById(userId)).thenReturn(Optional.empty());
        ResponseEntity<?> response = userController.deleteById(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());

        verify(iUserRepository, never()).save(any(User.class)); // Verificar que no se llamó al método save del repositorio
    }

    @Test
    public void testSave_ValidUser_ReturnsUserDTOWithId() {

        User savedUser = new User();
        savedUser.setId(UUID.fromString("a02ac32b-2965-4ed1-86fd-ab1b526ea9d1"));
        savedUser.setName("John");
        savedUser.setSurname("Doe");
        savedUser.setDni("123456789");
        savedUser.setEmail("john.doe@example.com");
        savedUser.setPhone("1234567890");

        when(iUserRepository.save(any(User.class))).thenReturn(savedUser);

        ResponseEntity<?> response = userController.save(mockUser());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserDTO userDTO = (UserDTO) response.getBody();
        assertEquals(UUID.fromString("a02ac32b-2965-4ed1-86fd-ab1b526ea9d1"), userDTO.getId());
        assertEquals("John", userDTO.getName());
        assertEquals("Doe", userDTO.getSurname());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals("123456789", userDTO.getDni());
        assertEquals("1234567890", userDTO.getPhone());

        verify(iUserRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUserById_UserExists_ReturnsSuccessMessage() {
        UUID userId = UUID.fromString(UUID.randomUUID().toString());

        UserDTO userDTO = mockUser2();

        User user = new User();
        user.setId(userId);
        user.setName("John");
        user.setSurname("Doe");
        user.setDni("123456789");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");

        when(iUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(iUserRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = userController.updateUserById(userId, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody());

        verify(iUserRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUserById_UserNotExists_ReturnsNotFoundMessage() {
        UUID userId = UUID.fromString(UUID.randomUUID().toString());

        UserDTO userDTO = mockUser2();

        when(iUserRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.updateUserById(userId, userDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());

        verify(iUserRepository, never()).save(any(User.class));
    }

    public User mockUser() {
        User mockUser = new User();
        mockUser.setName("John");
        mockUser.setSurname("Doe");
        mockUser.setDni("123456789");
        mockUser.setEmail("john.doe@example.com");
        mockUser.setPhone("1234567890");
        mockUser.setPassword("password");
        return mockUser;
    }

    public UserDTO mockUser2() {
        UserDTO mockUser2 = new UserDTO();
        mockUser2.setName("John");
        mockUser2.setSurname("Doe");
        mockUser2.setDni("123456789");
        mockUser2.setEmail("john.doe@example.com");
        mockUser2.setPhone("1234567890");
        return mockUser2;
    }
}
