package com.example.application.views.personel;

import com.example.application.views.model.Personel;
import com.example.application.views.service.PersonelService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class PersonelPresenter {

    private final PersonelService personelService;
    private PersonelView view;

    public PersonelPresenter(PersonelService personelService) {
        this.personelService = personelService;
    }

    public void setView(PersonelView view) {
        this.view = view;
    }

    public void loadPersonelList() {
        List<Personel> personelList = personelService.getPersonelList();
        view.updateGrid(personelList);
    }

    public void filterPersonel(String searchText) {
        List<Personel> filteredList = personelService.getPersonelList().stream()
                .filter(person -> person.getAd().toLowerCase().contains(searchText.toLowerCase()))
                .toList();
        view.updateGrid(filteredList);
    }
    
     //Üye silme işlemi servisten kaldırıyoruz ve listeyi güncelliyor loadPersonellist() .
    public void removePersonel(Personel personel) {
        personelService.removePersonel(personel);
        loadPersonelList();
    }

    //Üye Güncelleme İşlemi seçtiğimiz grid isim ve soyisim güncelleyebiliyoruz.
    public void updatePersonel(Personel updatedPersonel) {
        personelService.updatePersonel(updatedPersonel);
        loadPersonelList();
    }

    
    public void addRandomPersonel() {
        String[] names = {"Cem", "Selin", "Burak", "Ece", "Deniz", "Emre", "Selim"};
        String[] surnames = {"Koç", "Öztürk", "Şen", "Aslan", "Güneş", "Yıldırım", "Kutlu"};
        Random random = new Random();
        String randomName = names[random.nextInt(names.length)];
        String randomSurname = surnames[random.nextInt(surnames.length)];
        String randomTC = String.valueOf(10000000000L + random.nextInt(900000000));

        Personel yeniPersonel = new Personel(randomName, randomSurname, randomTC);
        personelService.addPersonel(yeniPersonel);
        loadPersonelList();
    }
}
