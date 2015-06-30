package com.fanqielaile.toms.support.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;

import java.util.Arrays;

/**
 * @author wdy
 */
public class ResourceAccessDecisionManager extends AffirmativeBased {
    public ResourceAccessDecisionManager(AccessDecisionVoter decisionVoters) {
        super(Arrays.asList(decisionVoters));
    }
}
