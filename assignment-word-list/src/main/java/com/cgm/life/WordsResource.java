package com.cgm.life;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
}