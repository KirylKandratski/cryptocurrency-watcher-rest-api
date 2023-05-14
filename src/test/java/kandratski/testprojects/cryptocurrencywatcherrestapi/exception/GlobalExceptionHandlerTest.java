package kandratski.testprojects.cryptocurrencywatcherrestapi.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    private NoSuchElementException noSuchElementException;
    private IllegalArgumentException illegalArgumentException;
    private IllegalStateException illegalStateException;
    private RuntimeException runtimeException;

    @BeforeEach
    void setUp() {
        noSuchElementException = new NoSuchElementException("Element not found");
        illegalArgumentException = new IllegalArgumentException("Invalid argument");
        illegalStateException = new IllegalStateException("Illegal state");
        runtimeException = new RuntimeException("Runtime error");
    }

    @Test
    void handleNoSuchElementException() {
        ResponseEntity<String> response = globalExceptionHandler.handleNoSuchElementException(noSuchElementException);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Element not found", response.getBody());
    }

    @Test
    void handleIllegalArgumentException() {
        ResponseEntity<String> response = globalExceptionHandler.handleIllegalArgumentException(illegalArgumentException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument", response.getBody());
    }

    @Test
    void handleIllegalStateException() {
        ResponseEntity<String> response = globalExceptionHandler.handleIllegalStateException(illegalStateException);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Illegal state", response.getBody());
    }

    @Test
    void handleRuntimeException() {
        ResponseEntity<String> response = globalExceptionHandler.handleRuntimeException(runtimeException);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Runtime error", response.getBody());
    }
}
