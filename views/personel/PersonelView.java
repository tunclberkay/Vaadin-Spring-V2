package com.example.application.views.personel;

import com.example.application.views.model.Personel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.List;

@PageTitle("Personel")
@Route("personel")
public class PersonelView extends VerticalLayout {

    private final PersonelPresenter presenter;
    private Grid<Personel> grid = new Grid<>(Personel.class);
    private TextField searchField = new TextField();
    private Button deleteButton = new Button("Sil", LineAwesomeIcon.TRASH_SOLID.create()); // Butonu Tanımlıyoruz.
    private Button updateButton = new Button("Güncelle", LineAwesomeIcon.EDIT.create());

    public PersonelView(PersonelPresenter presenter) {
        this.presenter = presenter;
        presenter.setView(this); 

        setSizeFull();
        configureGrid();
        add(createSearchBar(), grid,createToolbar(), createFloatingButton());


        presenter.loadPersonelList();
    }

  
    private void configureGrid() {
        grid.setColumns("ad", "soyad", "TC");

        grid.asSingleSelect().addValueChangeListener(event -> {
            boolean personelSecildi = event.getValue() != null;
            deleteButton.setEnabled(personelSecildi);
            updateButton.setEnabled(personelSecildi);
        });
    }

    private HorizontalLayout createSearchBar() {
        searchField.setPlaceholder("İsim ile ara...");
        searchField.setClearButtonVisible(true);
        searchField.setPrefixComponent(LineAwesomeIcon.SEARCH_SOLID.create());

        searchField.addValueChangeListener(event -> presenter.filterPersonel(event.getValue()));

        HorizontalLayout searchBar = new HorizontalLayout(searchField);
        searchBar.setWidthFull();
        return searchBar;
    }

    private HorizontalLayout createToolbar() {
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);

        deleteButton.addClickListener(event -> {
            Personel selectedPersonel = grid.asSingleSelect().getValue();
            if (selectedPersonel != null) {
                presenter.removePersonel(selectedPersonel);
            }
        });

        // Güncellemek için güncelle butonu oluşturuyoruz.
        updateButton.addClickListener(event -> {
            Personel selectedPersonel = grid.asSingleSelect().getValue();
            if (selectedPersonel != null) {
                showUpdateDialog(selectedPersonel);
            }
        });

        HorizontalLayout toolbar = new HorizontalLayout(deleteButton, updateButton);
        toolbar.setWidthFull();
        return toolbar;
    }

    // Güncelleme penceresi için Dialog ekliyoruz.
    // setReadOnly ile TC kimlik numarasını değiştirilmemesi için ekliyoruz.
    private void showUpdateDialog(Personel personel) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Personel Güncelle");

        TextField adField = new TextField("Ad");
        adField.setValue(personel.getAd());

        TextField soyadField = new TextField("Soyad");
        soyadField.setValue(personel.getSoyad());

        TextField tcField = new TextField("TC Kimlik No");
        tcField.setValue(personel.getTC());
        tcField.setReadOnly(true);

        Button saveButton = new Button("Kaydet", event -> {
            Personel updatedPersonel = new Personel(adField.getValue(), soyadField.getValue(), tcField.getValue());
            presenter.updatePersonel(updatedPersonel);
            dialog.close();
        });

        Button cancelButton = new Button("İptal", event -> dialog.close());

        FormLayout formLayout = new FormLayout(adField, soyadField, tcField);
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);

        dialog.add(formLayout, buttonLayout);
        dialog.open();
    }

    private Div createFloatingButton() {
        Button refreshButton = new Button("Yenile", LineAwesomeIcon.SYNC_SOLID.create());
        refreshButton.addClickListener(event -> presenter.addRandomPersonel());

        Div buttonContainer = new Div(refreshButton);
        buttonContainer.getStyle().set("position", "absolute");
        buttonContainer.getStyle().set("bottom", "20px");
        buttonContainer.getStyle().set("right", "20px");
        buttonContainer.getStyle().set("z-index", "1000");

        return buttonContainer;
    }

    public void updateGrid(List<Personel> personelList) {
        grid.setItems(personelList);
    }
}
