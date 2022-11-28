package com.salesianostriana.dam.trianafy.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PlaylistRequest {

private String name,description;

private Long id;

}
