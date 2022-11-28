package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.PlaylistDtoConverter;
import com.salesianostriana.dam.trianafy.dto.PlaylistRequest;
import com.salesianostriana.dam.trianafy.dto.PlaylistResponse;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
@RequestMapping("/list")
@RequiredArgsConstructor
@Tag(name = "PlaylistController", description = "Controlador de todas las peticiones hacia playlist")
public class PlaylistController {

    private final PlaylistDtoConverter dtoConverter;
    private final SongService songService;
    private final PlaylistService playlistService;


    @Operation(summary = "Ver todas las listas de reproducción existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se listan todas las listas de reprodución",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist.",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<PlaylistResponse>> getAllPlaylist() {
        List<Playlist> data = playlistService.findAll();

        if (data.isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            List<PlaylistResponse> result = data.stream().map(PlaylistResponse::of).collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }
    }


    @Operation(summary = "Crea una playlist con los atributos previamente dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear correctamente la playlist",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<PlaylistResponse> addOnePlaylist(@RequestBody PlaylistRequest playlistRequest) {

        Playlist nueva = dtoConverter.createPlaylistDtoToPlaylist(playlistRequest);

        playlistService.add(nueva);

        return ResponseEntity.status(HttpStatus.CREATED).body(PlaylistResponse.of(nueva));
    }

    @Operation(summary = "Buscar una playlist por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la Playlist",
                    content = {@Content(mediaType = "applicacion/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la Playlist",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getOneFindById(@PathVariable Long id) {

        if (playlistService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playlistService.findById(id).orElse(null));
    }


    @Operation(summary = "Eliminar una playlist por el ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No devuelve nada, sólo borra la playlist buscada por id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneFindById(@PathVariable Long id) {

        if (playlistService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        playlistService.delete(playlistService.findById(id).get());

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar una playlist buscada por el id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist.",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar la playlist.",
                    content = @Content),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Playlist> edit(@RequestBody Playlist p, @PathVariable Long id) {
        if (playlistService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.of(playlistService.findById(id)
                .map(encontrada -> {
                    encontrada.setName(p.getName());
                    encontrada.setDescription(p.getDescription());
                    playlistService.add(encontrada);
                    return encontrada;
                })
        );
    }

    @Operation(summary = "Añade a una playlist existente una canción existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado la canción con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear correctamente la playlist",
                    content = @Content),
    })

    @PostMapping("/{idPlaylist}/song/{idSong}")
    public ResponseEntity<Playlist> publishOneSongOnPlaylist(@PathVariable Long idPlaylist, @PathVariable Long idSong){
        Song cancionAniadida = songService.findById(idSong);
        Playlist playlistBuscada = playlistService.findById(idPlaylist);

        if(playlistBuscada.isEmpty){
            return ResponseEntity.notFound().build();
        }else{
            playlist.getSongs().add(cancionAniadida);
            return ResponseEntity.ok().body(playlistService.add(playlistBuscada));
        }

    }

}
