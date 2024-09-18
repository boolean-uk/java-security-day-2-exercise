package com.booleanuk.library.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "video_games")
public class VideoGame extends Item {
}
