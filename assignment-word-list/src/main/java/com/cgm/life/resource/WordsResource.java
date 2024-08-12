package com.cgm.life.resource;

import java.net.URI;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.reactive.RestHeader;

import com.cgm.life.service.WordListService;

import io.quarkus.panache.common.Sort;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/words")
public class WordsResource {

	private static final String ROLE_PREMIUM = "bigWords";
	private static final String ROLE_REGULAR = "endUser";

	private WordListService wordListService;

	@Inject
	public void setWordListService(WordListService wordListService) {
		this.wordListService = wordListService;
	}

	@GET
	@RolesAllowed({ ROLE_REGULAR, ROLE_PREMIUM })
	public Response getWords(@QueryParam("pageIndex") Integer pageIndex, @QueryParam("pageSize") Integer pageSize,
			@QueryParam("sortDirection") Sort.Direction sortDirection,
			@RestHeader("Content-Language") String languageCode, @Context SecurityContext securityContext) {

		//@formatter:off
		if(securityContext.isUserInRole(ROLE_PREMIUM)) {
	        return Response
	        		.ok(wordListService.getPremiumWords(pageIndex, pageSize, sortDirection, languageCode))
	        		.build();
		} else {
			return Response
	        		.ok(wordListService.getRegularWords(pageIndex, pageSize, sortDirection))
	        		.build();
		}
        //@formatter:on
	}

	@POST
	@RolesAllowed({ ROLE_REGULAR, ROLE_PREMIUM })
	public Response createWord(String wordValue, @QueryParam("premium") Boolean premium,
			@Context SecurityContext securityContext) {

		Long wordId;
		if (Boolean.TRUE.equals(premium)) {
			if (securityContext.isUserInRole(ROLE_PREMIUM)) {
				wordId = wordListService.saveWord(wordValue, premium);
			} else {
				return Response.status(Status.FORBIDDEN).build();
			}
		} else {
			wordId = wordListService.saveWord(wordValue, Boolean.FALSE);
		}

		//@formatter:off
		return Response
				.created(URI.create("/words/" + wordId))
				.build();
		//@formatter:on
	}
}