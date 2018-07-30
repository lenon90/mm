package com.example.mm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    JavaMailSender sender;
    @GetMapping("/")
    public String home(Model model){
        String path = System.getProperty("user.dir")
                +File.separator
                +"src"
                +File.separator
                +"main"
                +File.separator
                +"resources"
                +File.separator
                +"static"
                +File.separator;

        File file =new File(path);
        File[] files = file.listFiles();
        List<String> strings  = new ArrayList<>();
        for (File f : files) {
            strings.add(f.getName());
        }

model.addAttribute("images", strings);
        return "home";
    }
    @PostMapping("/upload")
    public String upload(
            @RequestParam("email") String email,
            @RequestParam("file") MultipartFile file) throws IOException, MessagingException {

        sendMail(email,file);
String path = System.getProperty("user.dir")
        +File.separator
        +"src"
        +File.separator
        +"main"
        +File.separator
        +"resources"
        +File.separator
        +"static"
        +File.separator;
file.transferTo(new File(path + file.getOriginalFilename()));

        return"redirect:/";

    }

    private void sendMail(String email, MultipartFile file) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setText("<h1>Hello</h1>this is sparta",true);
        helper.setSubject("mail from Andriy");
        helper.addAttachment(file.getOriginalFilename(),file);
        helper.setTo(email);

        sender.send(message);
    }

}
