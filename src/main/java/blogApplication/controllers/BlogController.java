package blogApplication.controllers;

import blogApplication.models.Post;
import blogApplication.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    public PostRepository postRepository;

    @GetMapping("/blog")
    public String getBlogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/about")
    public String getAboutUs(){
        return "about";
    }

    @GetMapping("/blog/add")
    public String addPost(){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String addPostHandler(@RequestParam(name="title") String title, @RequestParam(name="anons") String anons,
                                 @RequestParam(name="full_text") String full_text){
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog_info")
    public String getBlogInfo(@RequestParam(name="b_id") String id, Model model){
        Long postId = Long.parseLong(id);
        if(!postRepository.existsById(postId)){
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(postId);
        List<Post> listPosts = new ArrayList<>();

        post.ifPresent(listPosts::add);
        model.addAttribute("items", listPosts);
        return "blog-info";
    }

    @GetMapping("/update_item")
    public String getUpdatePostPage(@RequestParam(name="id") Long id, Model model){
        Optional<Post> post = postRepository.findById(id);
        List<Post> posts = new ArrayList<>();
        post.ifPresent(posts::add);

        model.addAttribute("posts", posts);
        return "update-post";
    }

    @PostMapping("/blog_update")
    public String editPost(@RequestParam(name="id") Long id, @RequestParam(name="title") String title,
                           @RequestParam(name="anons") String anons, @RequestParam(name="full_text") String full_text,
                           Model model){
        Post post = postRepository.findById(id).get();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @GetMapping("/delete_item")
    public String deletePost(@RequestParam(name="id") Long id){
        Post post = postRepository.findById(id).get();
        postRepository.delete(post);

        return "redirect:/blog";
    }
}
