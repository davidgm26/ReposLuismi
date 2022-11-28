package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SongDtoConverter {

    public static Song createSongDtoToSong(SongRequest songDto){
        return Song.builder()
                   .year(songDto.getYear())
                   .title(songDto.getTitle())
                   .album(songDto.getAlbum())
                   .build();
    }


}
