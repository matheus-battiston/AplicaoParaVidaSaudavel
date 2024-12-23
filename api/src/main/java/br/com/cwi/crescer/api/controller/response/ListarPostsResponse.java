package br.com.cwi.crescer.api.controller.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListarPostsResponse {
    List<PostResponse> listaDePosts = new ArrayList<>();
}
