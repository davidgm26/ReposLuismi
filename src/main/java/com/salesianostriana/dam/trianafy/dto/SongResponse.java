package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @Builder
@NoArgsConstructor
public class SongResponse {

    private Long id;
    private String artistName;
    private String album;
    private String year;
    private String title;

    public static SongResponse of (Song s) {

        SongResponse result = new SongResponse();


        if (s.getArtist() == null) {

            return result.builder()
                    .id(s.getIdSong())
                    .artistName("Undefined")
                    .album(s.getAlbum())
                    .year(s.getYear())
                    .title(s.getTitle())
                    .build();

        } else {

            return result.builder()
                    .id(s.getIdSong())
                    .artistName(s.getArtist().getName())
                    .album(s.getAlbum())
                    .year(s.getYear())
                    .title(s.getTitle())
                    .build();
        }

    }


}
