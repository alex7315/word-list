package com.cgm.life;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cgm.life.service.WordListService;

import io.quarkus.panache.common.Sort;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/words")
public class WordsResource {	
	
	private static final Boolean PREMIUM = Boolean.TRUE;
	private static final Boolean REGULAR = Boolean.FALSE;
	
	private WordListService wordListService;
	
	
    
	@Inject
    public void setWordListService(WordListService wordListService) {
		this.wordListService = wordListService;
	}


	@GET
    public Response getWords(@QueryParam("pageNumber") Integer pageNumber, @QueryParam("sortDirection") Sort.Direction sortDirection) {
    	//@formatter:off
        return Response
        		.ok(wordListService.getWords(pageNumber, sortDirection))
        		.build();
        //@formatter:on
    }
	
	@POST
	public Response createWord(String wordValue) {		
		Long wordId = wordListService.saveWord(wordValue, REGULAR);
		
		//@formatter:off
		return Response
				.created(URI.create("/words/" + wordId))
				.build();
		//@formatter:on
	}
}