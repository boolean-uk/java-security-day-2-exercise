package com.booleanuk.payload.response;

import com.booleanuk.api.model.VideoGame;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class VideoGameListResponse extends Response<List<VideoGame>> {
}
