package com.sanya.mts;

import com.sanya.mts.tariffs_manager.entities.ArchivedNews;
import com.sanya.mts.tariffs_manager.entities.News;
import com.sanya.mts.tariffs_manager.repositories.ArchivedNewsRepository;
import com.sanya.mts.tariffs_manager.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;

@Controller
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.0.103:3000"})
public class NewsController {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    ArchivedNewsRepository archivedNewsRepository;

    private Integer lastId = -1;

    private ArchivedNews moveToArchive(News post){
        return new ArchivedNews(post.getId(), post.getArticle(), post.getText(), post.getRelated_tariffs(), post.getDate());
    }

    private News unarchive(ArchivedNews post){
        return new News(post.getId(), post.getArticle(), post.getText(), post.getRelated_tariffs(), post.getDate());
    }


//    @RequestMapping(path="/getallposts")
//    public String showNews(Model model){
//        model.addAttribute("posts", newsRepository.findAll());
//        model.addAttribute("archived", archivedNewsRepository.findAll());
//        return "allposts";
//    }

    @RequestMapping(path="/allactualposts")
    public @ResponseBody Iterable<News> getActual(){
        return newsRepository.findAll();
    }

    @RequestMapping(path="/allarchivedposts")
    public @ResponseBody Iterable<ArchivedNews> getArchived(){
        return archivedNewsRepository.findAll();
    }

    @RequestMapping(path = "/createpostpage")
    public String createPostPage(Model model) throws IOException {
        model.addAttribute("post", new News());
        return "createpost";
    }

    @RequestMapping(path="/createpost")
    public @ResponseBody News createPost(
            @ModelAttribute News post,
            Model model
            ){
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
        post.setDate(new Timestamp(currentDate.getTime()));

        newsRepository.save(post);

        return post;
    }

    @RequestMapping(path="/removepost")
    public @ResponseBody String removePost(
            @RequestParam(value = "path")String path,
            @RequestParam(value = "postId")Integer id
    ){
        if(path.equals("actual")){
            News del = newsRepository.findById(id).get();
            newsRepository.delete(del);
        }else if(path.equals("archived")){
            ArchivedNews del = archivedNewsRepository.findById(id).get();
            archivedNewsRepository.delete(del);
        }

        return "Пост удалён";
    }

    @RequestMapping(path="/archivepost")
    public @ResponseBody News archivePost(
            @RequestParam(value = "postId")Integer id
    ){
        News arch = newsRepository.findById(id).get();
        newsRepository.delete(arch);
        archivedNewsRepository.save(moveToArchive(arch));
        return arch;
    }

    @RequestMapping(path="/unarchivepost")
    public @ResponseBody ArchivedNews unarchivePost(
            @RequestParam(value="postId")Integer id
            ){
        ArchivedNews unarh = archivedNewsRepository.findById(id).get();
        archivedNewsRepository.delete(unarh);
        newsRepository.save(unarchive(unarh));
        return unarh;
    }

    @RequestMapping(path="/modifypost/{id}")
    public @ResponseBody News modifyPost(
            @PathVariable("id")Integer id,
            Model model,
            @ModelAttribute News post
    ){
        model.addAttribute("post", post);
        newsRepository.deleteById(id);
        post.setId(id);

        return newsRepository.save(post);
    }

}
