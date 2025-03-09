package umc.product.domain.member.strategy.handler;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.client.SocialMemberClient;
import umc.product.domain.member.strategy.LoginStrategy;

/**
 * client와 strategy를 묶어서 저장하기 위한 class
 */
@Builder
@Getter
public class LoginHandler {
    private SocialMemberClient client;
    private LoginStrategy strategy;
}
