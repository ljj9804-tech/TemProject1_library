package com.library.project.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type;

    private String keyword;

    // 💡 추가된 필드: 정렬 조건을 담습니다. (예: id, pubdate, recommend 등)
    private String sort;

    private String link;

    // 💡 추가된 메서드: sort 값이 없을 때 기본값(id)을 반환하게 설정
    public String getSort() {
        if (sort == null || sort.isEmpty()) {
            return "id";
        }
        return sort;
    }

    public String[] getTypes() {
        if(type == null || type.isEmpty()) {
            return null;
        }
        return type.split("");
    }

    public Pageable getPageable(String... props) {
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    public String getLink() {
        if(link == null || link.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("page=").append(this.page);
            builder.append("&size=").append(this.size);

            if(type != null && !type.isEmpty()) {
                builder.append("&type=").append(type);
            }

            if(keyword != null && !keyword.isEmpty()){
                try {
                    builder.append("&keyword=").append(URLEncoder.encode(keyword,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            // 💡 추가된 로직: URL 링크 생성 시 sort 값도 포함시킵니다.
            if(sort != null && !sort.isEmpty()){
                builder.append("&sort=").append(sort);
            }

            link = builder.toString();
        }
        return link;
    }
}