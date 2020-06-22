package blogApplication.controllers;

import blogApplication.models.Car;
import blogApplication.models.Maker;
import blogApplication.models.Post;
import blogApplication.models.ShopLocation;
import blogApplication.repo.CarRepository;
import blogApplication.repo.MakerRepository;
import blogApplication.repo.PostRepository;
import blogApplication.repo.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/upload/";

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public MakerRepository makerRepository;

    @Autowired
    public ShopRepository shopRepository;

    @Autowired
    public CarRepository carRepository;

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

    @GetMapping("/file-form")
    public String getFileForm(){
        return "file-form";
    }

    /*@PostMapping("/file-form-handle")
    public ModelAndView handleFileData(@RequestParam(name="myFile") MultipartFile multipartFile) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Data = encoder.encode(multipartFile.getBytes());
        ModelAndView mv = new ModelAndView("show-files");
        mv.addObject("file", base64Data);

        return mv;
    }*/

    @PostMapping("file-form-handle")
    public String getDataAndPlaceToDir(@RequestParam(name="myFile") MultipartFile multipartFile,
                                       Model model) throws IOException, InterruptedException {
        //new File(uploadDir + "/roman").mkdir();
        Path originalPath = Paths.get(uploadDir + "/roman");

        if(!Files.exists(originalPath)){
            Files.createDirectory(originalPath);
        }

        Path path = Paths.get(uploadDir + "roman", multipartFile.getOriginalFilename());
        Files.write(path, multipartFile.getBytes());

        Thread.sleep(1000);

        StringBuilder sb = new StringBuilder();
        sb.append(multipartFile.getOriginalFilename());

        //ModelAndView mv = new ModelAndView("show-files");
        //mv.addObject("file", sb);
        model.addAttribute("file", sb.toString());

        return "show-files";
    }

    @GetMapping("/cars")
    public String getCars(Model model){
        Maker maker = makerRepository.findById(1).get();
        List<Car> cars = maker.getCars();
        model.addAttribute("cars", cars);

        return "cars";
    }

    @GetMapping("/cars_locations")
    public String getCarsLocations(Model model){
        ShopLocation location = shopRepository.findById(3).get();
        List<Car> cars = location.getCars();
        model.addAttribute("cars", cars);
        return "cars_locations";
    }

    @GetMapping("/locations_cars")
    public String getLocationsCars(Model model){
        Car car = carRepository.findById(2).get();
        List<ShopLocation> locations = car.getLocation();

        model.addAttribute("locations", locations);
        return "locations_cars";
    }

    @GetMapping("/get_resources")
    public void getResources(HttpServletResponse response) throws IOException {
        Path path = Paths.get(uploadDir + "2.jpg");

        response.setContentType("image/jpg");
        Files.copy(path, response.getOutputStream());
    }
}
