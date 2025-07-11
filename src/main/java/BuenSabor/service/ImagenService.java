package BuenSabor.service;

import BuenSabor.dto.imagen.ImagenRespuestaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImagenService {

    public ResponseEntity<ImagenRespuestaDTO> subirImagen(MultipartFile file) {


        if (file.isEmpty()) {
            ImagenRespuestaDTO respuesta = new ImagenRespuestaDTO(null,"El archivo está vacío");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/pjpeg") || contentType.equals("image/webp"))) {
            ImagenRespuestaDTO respuesta = new ImagenRespuestaDTO(
                    null,
                    "Formato de archivo no soportado. Solo se permiten .jpg, .pjpeg o .webp");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }

        try {
            String uploadDir = "uploads/images";
            File uploadPath = new File(uploadDir);

            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath);

            ImagenRespuestaDTO respuesta = new ImagenRespuestaDTO(fileName,"Imagen subida exitosamente");
            return ResponseEntity.ok(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ImagenRespuestaDTO(null,"Error al subir la imagen"));
        }
    }
}
