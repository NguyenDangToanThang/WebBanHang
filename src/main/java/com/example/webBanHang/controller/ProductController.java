package com.example.webBanHang.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.webBanHang.dto.ProductDto;
import com.example.webBanHang.model.Product;
import com.example.webBanHang.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private Cloudinary cloudinary;

    private Map upload(ProductDto productDto) throws IOException {
        Map r = this.cloudinary.uploader().upload(productDto.getImage().getBytes(),
                ObjectUtils.asMap("resource_type","auto"));
        return r;
    }

    @Secured("ADMIN")
    @GetMapping("/admin-page/products/add")
    public String getProduct(Model model){
        model.addAttribute("product", new Product());
        return "product/add";
    }
    @Secured("ADMIN")
    @GetMapping("/admin-page/products/update/{id}")
    public String getUpdateProduct(@PathVariable Long id , Model model){
        Optional<Product> product = productService.getProductById(id);
        model.addAttribute("product",product);
        return "product/update";
    }

    @Secured("ADMIN")
    @PostMapping("/admin-page/products/add")
    public String addProduct(@ModelAttribute("product") ProductDto productDTO, Model model) {

        MultipartFile image = productDTO.getImage();

        if (image.isEmpty()) {
            model.addAttribute("message", "No image selected");
            return "product/add";
        }
        try {
            // Đặt tên ảnh vào đối tượng Product
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setImage((String) upload(productDTO).get("secure_url"));

            productService.add(product);

            model.addAttribute("message", "Product added successfully");
            return "redirect:/admin-page";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error uploading image");
            return "product/add";
        }
    }

    @Secured("ADMIN")
    @PostMapping("/admin-page/products/update")
    public String updateProduct(
            @ModelAttribute ProductDto productDto,
            Model model ,
            @RequestParam("id") Long id,
            @RequestParam(value = "imagePathOld", required = false) String imagePathOld
    ) {

        Product product = new Product();
        product.setId(id);
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        if(productDto.getImage().isEmpty()) {
            product.setImage(imagePathOld);
        }
        else {
            try{
                product.setImage((String) upload(productDto).get("secure_url"));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        productService.update(product);
        return "redirect:/admin-page";
    }
    @Secured("ADMIN")
    @PostMapping("/admin-page/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        // Logic to delete product (only accessible to ADMIN)
        productService.delete(id);
        model.addAttribute("message", "Product deleted successfully");
        return "redirect:/admin-page";
    }
}
