package tariffs_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tariffs_manager.entities.News;
import tariffs_manager.repositories.ArchivedNewsRepository;
import tariffs_manager.repositories.NewsRepository;

import java.io.File;
import java.io.IOException;
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

    @RequestMapping(path="/createpostdemo")
    public String createPostDemo(
            @ModelAttribute News post,
            Model model,
            @RequestParam(value = "file")MultipartFile file
            ) throws IOException {

        model.addAttribute("post", post);
        if(lastId == -1){
            final Iterator<News> nIt = newsRepository.findAll().iterator();

            News last = nIt.next();

            while(nIt.hasNext()){
                last = nIt.next();
            }

            lastId = last.getId();
        }

        post.setId(++lastId);

        if(file!=null){
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String fileUUID = UUID.randomUUID().toString();
            String resultFileName = fileUUID + "." + file.getOriginalFilename();

            file.transferTo(new File(resultFileName));

            post.setFilePath(resultFileName);
        }

        newsRepository.save(post);

        return "allposts";
    }
}
