package com.example.agritrack.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.agritrack.Activities.FinanceActivity;  // ✅ AJOUTE ÇA

import com.example.agritrack.Activities.LoginActivity;
import com.example.agritrack.Activities.NotificationsActivity;
import com.example.agritrack.Activities.ProfileActivity;
import com.example.agritrack.Activities.TerrainListActivity;
import com.example.agritrack.Activities.EquipmentListActivity;
import com.example.agritrack.R;
import com.example.agritrack.Models.DashboardModule;
import com.example.agritrack.Utils.ModuleCardHelper;
import com.example.agritrack.Utils.StorageHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.example.agritrack.Activities.FinanceActivity;
import com.example.agritrack.Activities.SplashCaptchaActivity;  // ✅ AJOUTE ÇA
import com.example.agritrack.Activities.LoginActivity;

/**
 * Activité principale (Accueil) de l'application AgriTrack
 * Affiche les différents modules disponibles sous forme de cartes
 */
public class AccueilActivity extends AppCompatActivity {

    private StorageHelper storageHelper;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Initialiser StorageHelper
        storageHelper = new StorageHelper(this);

        // Vérifier si l'utilisateur est connecté
        if (!storageHelper.isUserLoggedIn()) {
            Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Initialiser les composants
        initializeComponents();
    }

    /**
     * Initialise tous les composants de l'interface
     */
    private void initializeComponents() {
        updateWelcomeText();
        setupBottomNavigation();
        setupModuleCards();

    }

    /**
     * Met à jour le texte de bienvenue avec les infos de l'utilisateur
     */
    private void updateWelcomeText() {
        TextView welcomeText = findViewById(R.id.welcomeText);
        String userName = storageHelper.getUserName();
        String farmName = storageHelper.getFarmName();
        welcomeText.setText("Bienvenue, " + userName + "!\n" + farmName);
    }

    /**
     * Configure la barre de navigation inférieure
     */
    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    return true;
                } else if (itemId == R.id.nav_notifications) {
                    startActivity(new Intent(AccueilActivity.this, NotificationsActivity.class));
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    startActivity(new Intent(AccueilActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Configure toutes les cartes de modules
     */
    private void setupModuleCards() {
        // Mapper explicitement les cartes visibles dans activity_accueil.xml
        ModuleCardHelper.setupModuleCard(
            this,
            findViewById(R.id.card_land),
            new DashboardModule("🌾", "Terrain", "Gestion des terrains", "#F57F17", TerrainListActivity.class)
        );

        ModuleCardHelper.setupModuleCard(
            this,
            findViewById(R.id.card_animals),
            new DashboardModule("🐄", "Animaux", "Gestion du bétail", "#1F5C2E", PlaceholderActivity.class)
        );

        ModuleCardHelper.setupModuleCard(
            this,
            findViewById(R.id.card_plants),
            new DashboardModule("🌱", "Cultures", "Suivi des récoltes", "#F57F17", PlaceholderActivity.class)
        );

        ModuleCardHelper.setupModuleCard(
            this,
            findViewById(R.id.card_food),
            new DashboardModule("🍽️", "Alimentation", "Nourriture animaux", "#F57F17", PlaceholderActivity.class)
        );

        ModuleCardHelper.setupModuleCard(
            this,
            findViewById(R.id.card_irrigation),
            new DashboardModule("💧", "Irrigation", "Gestion de l'irrigation", "#0277BD", PlaceholderActivity.class)
        );

        ModuleCardHelper.setupModuleCard(
            this,
            findViewById(R.id.card_medicines),
            new DashboardModule("💊", "Médicaments", "Soins & vaccins", "#C62828", PlaceholderActivity.class)
        );

        ModuleCardHelper.setupModuleCard(
            this,
            findViewById(R.id.card_equipment),
            new DashboardModule("🚜", "Matériel", "Outils & équipements", "#0277BD", EquipmentListActivity.class)
        );

        ModuleCardHelper.setupModuleCard(
                this,
                findViewById(R.id.card_finance),
                new DashboardModule("💰", "Finances", "Dépenses & revenus", "#6A1B9A", SplashCaptchaActivity.class)
        );


    }





    @Override
    protected void onResume() {
        super.onResume();
        // Mettre à jour l'affichage quand on revient sur l'activité
        if (storageHelper != null && storageHelper.isUserLoggedIn()) {
            updateWelcomeText();
        }
    }
}