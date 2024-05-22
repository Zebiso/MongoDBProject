package com.joseg.mongoproject.config;

import com.joseg.mongoproject.domain.Post;
import com.joseg.mongoproject.domain.User;
import com.joseg.mongoproject.dto.AuthorDTO;
import com.joseg.mongoproject.dto.CommentDTO;
import com.joseg.mongoproject.repository.PostRepository;
import com.joseg.mongoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository ;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Clara","maria@gmail.com");
        User juliana = new User(null, "Juliana Macedo", "juhmacedo@outlook.com");
        User jose = new User(null, "José Gabriel", "josegab12@outlook.com");
        userRepository.saveAll(Arrays.asList(maria,juliana,jose));

        Post post1 = new Post(null,sdf.parse("15/05/2024"), "Teste", "Teste teste teste teste teste teste", new AuthorDTO(jose));
        Post post2 = new Post(null,sdf.parse("14/05/2024"), "Ola", "Ola, tudo bem", new AuthorDTO(maria));
        Post post3 = new Post(null,sdf.parse("15/05/2024"), "Boa tarde", "Boa tarde para todos", new AuthorDTO(maria));

        CommentDTO c1 = new CommentDTO("teste também", sdf.parse("15/05/2024"), new AuthorDTO(maria));
        CommentDTO c2 = new CommentDTO("Está tudo bem sim", sdf.parse("20/05/2024"), new AuthorDTO(jose));
        CommentDTO c3 = new CommentDTO("Boa tarde, Maria", sdf.parse("16/05/2024"), new AuthorDTO(juliana));

        post1.getComments().addAll(Arrays.asList(c1));
        post2.getComments().addAll(Arrays.asList(c2));
        post3.getComments().addAll(Arrays.asList(c3));

        postRepository.saveAll(Arrays.asList(post1, post2, post3));

        maria.getPosts().addAll(Arrays.asList(post2,post3));
        jose.getPosts().addAll(Arrays.asList(post1));
        userRepository.saveAll(Arrays.asList(maria, jose));

    }
}
