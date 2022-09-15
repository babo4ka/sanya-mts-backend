package com.sanya.mts;

import com.sanya.mts.tariffs_manager.entities.News;
import com.sanya.mts.tariffs_manager.repositories.ArchivedNewsRepository;
import com.sanya.mts.tariffs_manager.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;
import java.util.UUID;

@Controller
public class NewsController {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    ArchivedNewsRepository archivedNewsRepository;

    private Integer lastId = -1;

    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping(path="/getallposts")
    public String showNews(Model model){
        model.addAttribute("posts", newsRepository.findAll());
        return "allposts";
    }

    @RequestMapping(path = "/createpostpage")
    public String createPostPage(Model model){
        model.addAttribute("post", new News());
        return "createpost";
    }

    @RequestMapping(path="/createpostdemo")
    public String createPostDemo(
            @ModelAttribute News post,
            Model model,
            @RequestParam(value = "file")MultipartFile file
            ) throws IOException {

        model.addAttribute("post", post);
        if(lastId == -1){
            final Iterator<News> nIt = newsRepository.findAll().iterator();

            News last = nIt.hasNext()?nIt.next():null;

            if(last==null){
                lastId = 1;
            }else{
                while(nIt.hasNext()){
                    last = nIt.next();
                }

                lastId = last.getId();
            }
        }

        post.setId(++lastId);
        java.util.Date currentDate = new java.util.Date();
        post.setDate(new Date(currentDate.getTime()));

        if(file!=null){
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String resultFileName = post.getArticle().replace(' ', '_') + post.getId() + "." + file.getOriginalFilename();

            System.out.println(new File("").getAbsolutePath() + "/" + uploadPath + resultFileName);
            file.transferTo(new File(new File("").getAbsolutePath() + "/" + uploadPath + resultFileName));

            post.setFilePath(resultFileName);
        }

        newsRepository.save(post);

        return "allposts";
    }
}
