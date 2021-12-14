package hello.Itemservice.web.basic;


import hello.Itemservice.domain.item.Item;
import hello.Itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository store;

    @GetMapping
    public String items(Model model) {

        List<Item> all = store.findAll();
        model.addAttribute("list", all);
        return "basic/items";
    }


    @GetMapping("/add")
    public String addItem(Model model) {
        System.out.println("come");
        return "basic/items";
    }

    @GetMapping("/{id}")
    public String addItem(@PathVariable Long id, Model model) {
        System.out.println("id :" + id);
        return "basic/items";
    }


    @PostConstruct
    public void init() {
        Item item = new Item("1", 1000, 10);
        Item item1 = new Item("2", 1000, 10);
        store.saveAll(Arrays.asList(item, item1));
    }
}
