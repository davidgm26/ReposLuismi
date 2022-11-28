package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;
    private final SongService songService;

    private final ArtistRepository artistRepo;

    @Operation(summary = "Crea un artista con la información proporcionada en el cuerpo de la petición")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Artista agregado con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido agregar el artista",
                    content = @Content),

    })
    @PostMapping("/")
    public ResponseEntity<Artist> createNewArtist(@RequestBody Artist a) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artistService.add(a));
    }

    @Operation(summary = "Listar todos los artistas guardadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Muestra una lista con todos los artistas guardados",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado artistas",
                    content = @Content),

    })
    @GetMapping("/")
    public ResponseEntity<List<Artist>> listAllArtists() {
        return ResponseEntity.ok(artistService.findAll());
    }

    @Operation(summary = "Muestra un artista buscado por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Artista encontrado con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artista",
                    content = @Content),

    })
    @GetMapping("/{id}")
    public ResponseEntity<Artist> showOneArtistInfo(@PathVariable Long id) {
        return ResponseEntity.of(artistService.findById(id));
    }

    @Operation(summary = "Modifica un Artista buscado por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Artista modificado con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artista",
                    content = @Content),

    })
    @PutMapping("/{id}")
    public ResponseEntity<Artist> modifiyArtistInfo(@PathVariable Long id, @RequestBody Artist a) {
        return ResponseEntity.of(artistService.findById(id).map(old -> {
                            old.setName(a.getName());
                            return Optional.of(artistService.add(old));
                        })
                        .orElse(Optional.empty())
        );
    }

    @Operation(summary = "Borra un artista específico buscado por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Artista borrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artista",
                    content = @Content),

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Artist> deleteOneArtist(@PathVariable Long id) {

        Artist artist = artistService.findById(id).get();
        songService.findAll()
                .stream()
                .filter(song -> song.getArtist().equals(artist))
                .forEach(song -> song.setArtist(null));

        artistRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
