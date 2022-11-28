package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaylistDtoConverter {

    public  Playlist createPlaylistDtoToPlaylist(PlaylistRequest playlistRequest) {

        return Playlist.builder()
                .id(playlistRequest.getId())
                .name(playlistRequest.getName())
                .description(playlistRequest.getDescription())
                .build();
    }






}
