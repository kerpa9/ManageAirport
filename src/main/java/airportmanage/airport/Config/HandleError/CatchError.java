package airportmanage.airport.Config.HandleError;

import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import airportmanage.airport.Config.HandleException.HandleException;
import jakarta.persistence.EntityNotFoundException;

public class CatchError {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> batRequest400(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> "Verify the added data and identify the error")
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors + "Error, data not found");
    }

    @ExceptionHandler(HandleException.class)
    public ResponseEntity<?> manageErrorValidate(HandleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
