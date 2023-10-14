package springmvcweb.mvcitemservice.web.basic;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springmvcweb.mvcitemservice.domain.item.Item;
import springmvcweb.mvcitemservice.domain.item.ItemRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired//스프링에서 생성자가 이렇게 하나밖에 없으면 오토와일드생략가능
//    public BasicItemController(ItemRepository itemRepository) {//final타입객체 자동생성자 어노테이션때문에 생략가능
//        this.itemRepository = itemRepository;
//    }

    @GetMapping//상품목록
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")//상품상세보기
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")//상품등록폼
    public String addForm() {
        return "basic/addForm";
    }

    //@PostMapping("/add")//상품등록
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /** 위아래 같은거 **/

    //@PostMapping("/add")//상품등록
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {//Model model생략가능 자동으로 넣어주기때문

        itemRepository.save(item);
        model.addAttribute("item", item);  //생략가능
        return "basic/item";
    }
    /** 위아래 같은거 **/

    //@PostMapping("/add")//상품등록
    public String addItemV3(@ModelAttribute Item item) {//Model model생략가능 자동으로 넣어주기때문
//명시안하면 기본값으로 클래스이름 첫글자만 소문자로 바꿔서 그 이름으로 저장(item)
        itemRepository.save(item);
       // model.addAttribute("item", item);  //생략가능
        return "basic/item";               //어노테이션도 생략가능
    }
/**리다이렉트를 해야한다.안그러면 새로고침할 때마다 post요청이 계속 반복돼서 상품저장됨 아래처럼 바꾸자*/

    //@PostMapping("/add")//상품등록
    public String addItemV5(@ModelAttribute Item item) {//Model model생략가능 자동으로 넣어주기때문
//명시안하면 기본값으로 클래스이름 첫글자만 소문자로 바꿔서 그 이름으로 저장(item)
        itemRepository.save(item);
        // model.addAttribute("item", item);  //생략가능
        return "redirect:/basic/items/"+item.getId();               //어노테이션도 생략가능
    }

    @PostMapping("/add")//상품등록
    public String addItemV6(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {//Model model생략가능 자동으로 넣어주기때문
//명시안하면 기본값으로 클래스이름 첫글자만 소문자로 바꿔서 그 이름으로 저장(item)
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        // model.addAttribute("item", item);  //생략가능
        return "redirect:/basic/items/{itemId}";
    }              //어노테이션도 생략가능



    @GetMapping("/{itemId}/edit")//상품수정 폼
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")//상품수정처리
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";

    }








    /**테스트용데이터 추가**/
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000, 10));
        itemRepository.save(new Item("itemB",20000, 20));
    }

}
