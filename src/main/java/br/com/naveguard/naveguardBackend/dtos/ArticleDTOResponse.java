package br.com.naveguard.naveguardBackend.dtos;

public record ArticleDTOResponse(Long id,
                                 String title,
                                 String content,
                                 String urlPhoto,
                                 UserMinDTO author) 
{

	
	
}
