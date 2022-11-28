package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafy.dto.SongRequest;
import com.salesianostriana.dam.trianafy.dto.SongResponse;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/song")
@Tag(name = "Song", description = "Controlador encargado de gestionar todo lo relacionado con las canciones")
public class SongController {

    private final SongService songService;
    private final ArtistService artistService;

    private final SongDtoConverter songDtoConverter;


    @Operation(summary = "Lista todas las canciones de la app")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Canciones listadas con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado canciones",
                    content = @Content),

    })
    @GetMapping("/")
    public ResponseEntity<List<SongResponse>> getAllSongs() {

        List<Song> datos = songService.findAll();

        if (datos.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<SongResponse> resultado = datos
                    .stream()
                    .map(SongResponse::of)
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(resultado);
        }
    }

    @Operation(summary = "Crea una canción con los atributos proporcionados anteriormente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado una nueva canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear la canción",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado al artista",
                    content = @Content),

    })
    @PostMapping("/")
    public ResponseEntity<SongResponse> addOneSong(@RequestBody SongRequest songRequest) {

        if (songRequest.getArtistId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Song newSong = songDtoConverter.createSongDtoToSong(songRequest);

        Artist artist = artistService.findById(songRequest.getArtistId()).orElse(null);

        newSong.setArtist(artist);

        newSong = songService.add(newSong);


        return ResponseEntity.status(HttpStatus.CREATED).body(SongResponse.of(newSong));

    }


    @Operation(summary = "Muestra la información de una canción buscada por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Muestra la cancion buscada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la cancion",
                    content = @Content),

    })
    @GetMapping("/{id}")
    public ResponseEntity<Song> getOneSongInfo(@PathVariable Long id) {
        return ResponseEntity.of(songService.findById(id));
    }

    @Operation(summary = "Actualiza los datos de una canción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Canción modificada con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado cancion",
                    content = @Content),

    })
    @PutMapping("/{id}")
    public ResponseEntity<SongResponse> updateOneSongInfo(
            @PathVariable Long id,
            @RequestBody SongRequest songRequest) {

        Song edit = SongDtoConverter.createSongDtoToSong(songRequest);

        Artist artist = artistService.findById(songRequest.getArtistId()).orElse(null);

        edit.setArtist(artist);

        return ResponseEntity.of(songService.findById(id)
                .map(song -> {
                    song.setArtist(edit.getArtist());
                    song.setAlbum((edit.getAlbum()));
                    song.setYear((edit.getYear()));
                    song.setTitle(edit.getTitle());

                    songService.add(song);

                    return SongResponse.of(song);
                }));

    }

    @Operation(summary = "Borra una canción buscada por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Canción borrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado la canción",
                    content = @Content),

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneSong(@PathVariable Long id) {
        songService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
