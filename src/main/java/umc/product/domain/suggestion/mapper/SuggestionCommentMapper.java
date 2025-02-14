package umc.product.domain.suggestion.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.dto.request.SuggestionCommentRequest;
import umc.product.domain.suggestion.dto.response.*;
import umc.product.domain.suggestion.entity.Suggestion;
import umc.product.domain.suggestion.entity.SuggestionComment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SuggestionCommentMapper {
    public SuggestionComment toSuggestionComment(Member member,
                                                 Suggestion suggestion,
                                                 Integer maxBundleId,
                                                 SuggestionCommentRequest suggestionCommentRequest) {
        return SuggestionComment.builder()
                .comment(suggestionCommentRequest.getComment())
                .depth(0L)
                .parentComment(null)
                .childComments(null)
                .member(member)
                .bundleId(maxBundleId != null ? maxBundleId + 1L : 1L)
                .suggestion(suggestion)
                .build();
    }

    public SuggestionComment toSuggestionChildComment(Member member,
                                                      Suggestion suggestion,
                                                      SuggestionCommentRequest suggestionCommentRequest,
                                                      SuggestionComment parentComment) {
        return SuggestionComment.builder()
                .comment(suggestionCommentRequest.getComment())
                .depth(parentComment.getDepth()+1)
                .parentComment(parentComment)
                .childComments(null)
                .bundleId(parentComment.getBundleId())
                .member(member)
                .suggestion(suggestion)
                .build();
    }

    public SuggestionCommentIdResponse toSuggestionCommentIdResponse(SuggestionComment suggestionComment) {
        return SuggestionCommentIdResponse.builder()
                .suggestionCommentId(suggestionComment.getId())
                .build();
    }

    public List<SuggestionCommentsResponse> toPostDetailCommentResultDTO(List<SuggestionComment> comments, Long depth) {

        return comments.stream()
                .filter(comment -> comment.getDepth().equals(depth))
                .map(comment -> {
                    List<SuggestionCommentsResponse> childCommentsDTO =
                            comment.getChildComments() != null
                                    ? toPostDetailCommentResultDTO(comment.getChildComments(), depth + 1)
                                    : new ArrayList<>();

                    return SuggestionCommentsResponse.builder()
                            .suggestionCommentId(comment.getId())
                            .comment(comment.getComment())
                            .createdAt(comment.getCreatedAt())
                            .comments(childCommentsDTO)
                            .memberName(comment.getMember().getName())
                            .memberNickName(comment.getMember().getNikeName())
                            .memberAvatarUrl(comment.getMember().getAvatarUrl())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
