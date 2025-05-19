package org.example.premier_projet_spring.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    //    public void sendEmailValidationToken(String to, String token) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Validation de votre compte PineApple Store");
//        message.setText("Merci de cliquer sur le lien suivant " +
//                "pour valider votre compte : http://localhost:8080/validate?token=" + token);
//        mailSender.send(message);
//    }
    public void sendEmailValidationToken(String to, String token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // Extraction du nom d'utilisateur à partir de l'email
            String username = to.split("@")[0];

            // Première lettre en majuscule pour un meilleur rendu
            username = username.substring(0, 1).toUpperCase() + username.substring(1);

            // Année courante
            int currentYear = Year.now().getValue();

            helper.setTo(to);
            helper.setSubject("🍍 Validation de votre compte PineApple Store 🍍");

            // Création du contenu HTML avec le token injecté manuellement
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <style>\n" +
                    "        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap');\n" +
                    "        \n" +
                    "        body {\n" +
                    "            font-family: 'Poppins', Arial, sans-serif;\n" +
                    "            color: #333;\n" +
                    "            line-height: 1.6;\n" +
                    "            background-color: #fffdf5;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 20px auto;\n" +
                    "            background-color: #fff;\n" +
                    "            border-radius: 10px;\n" +
                    "            overflow: hidden;\n" +
                    "            box-shadow: 0 4px 15px rgba(0,0,0,0.1);\n" +
                    "        }\n" +
                    "        .header {\n" +
                    "            background-color: #FFD700; /* Or */\n" +
                    "            padding: 20px;\n" +
                    "            text-align: center;\n" +
                    "            position: relative;\n" +
                    "        }\n" +
                    "        .header h1 {\n" +
                    "            color: #8B4513; /* Marron foncé */\n" +
                    "            margin: 0;\n" +
                    "            font-size: 28px;\n" +
                    "            font-weight: 700;\n" +
                    "            text-shadow: 1px 1px 2px rgba(0,0,0,0.1);\n" +
                    "        }\n" +
                    "        .emoji {\n" +
                    "            font-size: 24px;\n" +
                    "            margin: 0 5px;\n" +
                    "            animation: bounce 1s infinite alternate;\n" +
                    "            display: inline-block;\n" +
                    "        }\n" +
                    "        @keyframes bounce {\n" +
                    "            from { transform: translateY(0); }\n" +
                    "            to { transform: translateY(-10px); }\n" +
                    "        }\n" +
                    "        .content {\n" +
                    "            padding: 30px;\n" +
                    "            background-color: #FFFDE7; /* Jaune très clair */\n" +
                    "        }\n" +
                    "        .button {\n" +
                    "            display: inline-block;\n" +
                    "            background-color: #FF9800; /* Orange */\n" +
                    "            color: white;\n" +
                    "            padding: 12px 25px;\n" +
                    "            text-decoration: none;\n" +
                    "            border-radius: 25px;\n" +
                    "            margin-top: 15px;\n" +
                    "            font-weight: 600;\n" +
                    "            box-shadow: 0 4px 8px rgba(255, 152, 0, 0.3);\n" +
                    "            transition: all 0.3s ease;\n" +
                    "        }\n" +
                    "        .button:hover {\n" +
                    "            background-color: #F57C00;\n" +
                    "            transform: translateY(-2px);\n" +
                    "            box-shadow: 0 6px 12px rgba(255, 152, 0, 0.4);\n" +
                    "        }\n" +
                    "        .card {\n" +
                    "            background-color: white;\n" +
                    "            border-radius: 8px;\n" +
                    "            padding: 20px;\n" +
                    "            margin: 20px 0;\n" +
                    "            box-shadow: 0 2px 8px rgba(0,0,0,0.05);\n" +
                    "            border-left: 4px solid #FFD700;\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            text-align: center;\n" +
                    "            padding: 20px;\n" +
                    "            background-color: #FAF3E0;\n" +
                    "            font-size: 12px;\n" +
                    "            color: #8D6E63;\n" +
                    "            border-top: 1px dashed #E0E0E0;\n" +
                    "        }\n" +
                    "        .pineapple-decoration {\n" +
                    "            text-align: center;\n" +
                    "            padding: 10px 0;\n" +
                    "        }\n" +
                    "        .confetti {\n" +
                    "            position: absolute;\n" +
                    "            width: 10px;\n" +
                    "            height: 10px;\n" +
                    "            background-color: #FFD700;\n" +
                    "            opacity: 0.8;\n" +
                    "        }\n" +
                    "        #countdown {\n" +
                    "            font-weight: bold;\n" +
                    "            color: #FF5722;\n" +
                    "        }\n" +
                    "        .animated-link {\n" +
                    "            position: relative;\n" +
                    "            color: #FF9800;\n" +
                    "            text-decoration: none;\n" +
                    "            font-weight: 600;\n" +
                    "        }\n" +
                    "        .animated-link:after {\n" +
                    "            content: '';\n" +
                    "            position: absolute;\n" +
                    "            width: 100%;\n" +
                    "            transform: scaleX(0);\n" +
                    "            height: 2px;\n" +
                    "            bottom: -2px;\n" +
                    "            left: 0;\n" +
                    "            background-color: #FF9800;\n" +
                    "            transform-origin: bottom right;\n" +
                    "            transition: transform 0.3s ease-out;\n" +
                    "        }\n" +
                    "        .animated-link:hover:after {\n" +
                    "            transform: scaleX(1);\n" +
                    "            transform-origin: bottom left;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"header\">\n" +
                    "            <span class=\"emoji\">🍍</span>\n" +
                    "            <span class=\"fakeh1\">PineApple Store</span>\n" +
                    "            <span class=\"emoji\">🍍</span>\n" +
                    "        </div>\n" +
                    "        <div class=\"content\">\n" +
                    "            <div class=\"pineapple-decoration\">\n" +
                    "                🍍 🍍 🍍 🍍 🍍 🍍 🍍\n" +
                    "            </div>\n" +
                    "            <div class=\"card\">\n" +
                    "                <h2>Bonjour " + username + " 👋</h2>\n" +
                    "                <p>Merci pour votre inscription au PineApple Store !</p>\n" +
                    "                <p>Pour finaliser votre inscription et accéder à tous nos services exclusifs, veuillez cliquer sur le bouton ci-dessous :</p>\n" +
                    "            </div>\n" +
                    "            <div style=\"text-align: center;\">\n" +
                    "                <a href=\"http://localhost:4200/validate-email/" + token + "\" class=\"button\">✨ Valider mon compte ✨</a>\n" +
                    "            </div>\n" +
                    "            <p>Ce lien est valable pour <span id=\"countdown\">24 heures</span>.</p>\n" +
                    "            <p>Si le bouton ne fonctionne pas, vous pouvez copier et coller le lien suivant dans votre navigateur :</p>\n" +
                    "            <p><a href=\"http://localhost:4200/validate-email/" + token + "\" class=\"animated-link\">http://localhost:4200/validate?token=" + token + "</a></p>\n" +
                    "        </div>\n" +
                    "        <div class=\"footer\">\n" +
                    "            <p>🍍 © " + currentYear + " PineApple Store - Tous droits réservés 🍍</p>\n" +
                    "            <p>Cet email a été envoyé automatiquement, merci de ne pas y répondre.</p>\n" +
                    "            <div class=\"pineapple-decoration\">\n" +
                    "                🍍 🍍 🍍\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <!-- Note : Le JavaScript ne fonctionnera que dans certains clients email qui le supportent -->\n" +
                    "    <script>\n" +
                    "        // Animation pour le countdown (fonctionne dans certains clients email)\n" +
                    "        function updateCountdown() {\n" +
                    "            const now = new Date();\n" +
                    "            const tomorrow = new Date(now);\n" +
                    "            tomorrow.setDate(tomorrow.getDate() + 1);\n" +
                    "            \n" +
                    "            const diff = tomorrow - now;\n" +
                    "            const hours = Math.floor(diff / (1000 * 60 * 60));\n" +
                    "            const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));\n" +
                    "            \n" +
                    "            const countdownElement = document.getElementById('countdown');\n" +
                    "            if (countdownElement) {\n" +
                    "                countdownElement.textContent = hours + ' heures et ' + minutes + ' minutes';\n" +
                    "            }\n" +
                    "        }\n" +
                    "        \n" +
                    "        // Effet de confettis pour la header (fonctionne dans certains clients email)\n" +
                    "        function createConfetti() {\n" +
                    "            const header = document.querySelector('.header');\n" +
                    "            if (!header) return;\n" +
                    "            \n" +
                    "            for (let i = 0; i < 50; i++) {\n" +
                    "                const confetti = document.createElement('div');\n" +
                    "                confetti.classList.add('confetti');\n" +
                    "                confetti.style.left = Math.random() * 100 + '%';\n" +
                    "                confetti.style.top = Math.random() * 100 + '%';\n" +
                    "                confetti.style.backgroundColor = ['#FFD700', '#FF9800', '#FFEB3B', '#8B4513'][Math.floor(Math.random() * 4)];\n" +
                    "                confetti.style.transform = 'rotate(' + Math.random() * 360 + 'deg)';\n" +
                    "                header.appendChild(confetti);\n" +
                    "            }\n" +
                    "        }\n" +
                    "        \n" +
                    "        // Exécution des scripts si supportée\n" +
                    "        try {\n" +
                    "            updateCountdown();\n" +
                    "            createConfetti();\n" +
                    "            setInterval(updateCountdown, 60000); // Mise à jour chaque minute\n" +
                    "        } catch (e) {\n" +
                    "            // Silencieux en cas d'erreur\n" +
                    "        }\n" +
                    "    </script>\n" +
                    "</body>\n" +
                    "</html>";

            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email de validation", e);
        }
    }


}
