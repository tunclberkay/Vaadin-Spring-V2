package com.example.application.views.service;

import com.example.application.views.model.Personel;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonelService {
    private final List<Personel> personelList = new ArrayList<>(); 

    public PersonelService() { 
        loadDummyData(); 
    }

    private void loadDummyData() {
        personelList.add(new Personel("Ahmet", "Yılmaz", "14173139586"));
        personelList.add(new Personel("Mehmet", "Kaya", "14172536457"));
        personelList.add(new Personel("Ayşe", "Demir", "18524253865"));
        personelList.add(new Personel("Fatma", "Çelik", "19258459756"));
        personelList.add(new Personel("Hasan", "Güneş", "19422558459"));
        personelList.add(new Personel("Zeynep", "Arslan", "12258545952"));
        personelList.add(new Personel("Ali", "Doğan", "19252584595"));
        personelList.add(new Personel("Elif", "Koç", "19258459524"));
        personelList.add(new Personel("Mustafa", "Aydın", "19258459452"));
        personelList.add(new Personel("Hülya", "Şahin", "19258459452"));
    }

    public List<Personel> getPersonelList() {
        return personelList; 
    }
    
    public void addPersonel(Personel yeniPersonel) {
        personelList.add(yeniPersonel); 
    }

    //Üye Güncellemesi için yarattığımız metot.
    // TC kimlik no eşleşirse güncellenmiş nesneyi indexe koyup döngüden çıkıyor.
    public void updatePersonel(Personel updatedPersonel) {
        for (int i = 0; i < personelList.size(); i++) {
            if (personelList.get(i).getTC().equals(updatedPersonel.getTC())) {
                personelList.set(i, updatedPersonel);
                break;
            }
        }
    }

     //Üye silmemiz için yarattığımız metodu ekliyoruz. 
     //Personel nesnesinde Ad kısımları aynı olabileceği için TC kimlik numarasına göre listeden kaldırılmasını sağlıyoruz.
    public void removePersonel(Personel personel) {
        personelList.removeIf(p -> p.getTC().equals(personel.getTC()));
    }
}
