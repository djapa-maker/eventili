<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230406011534 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE transaction (id_trans INT AUTO_INCREMENT NOT NULL, user_id INT NOT NULL, valeur_trans DOUBLE PRECISION NOT NULL, devis ENUM(\'USD\', \'EUR\', \'JPY\', \'GBP\', \'CHF\', \'CAD\', \'AUD\', \'NZD\'), date_trans DATE NOT NULL, mode_trans ENUM(\'Square\', \'Stripe\', \'Amazon Pay\', \'Google Pay\'), montant_tot DOUBLE PRECISION NOT NULL, INDEX IDX_723705D1A76ED395 (user_id), PRIMARY KEY(id_trans)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE transaction ADD CONSTRAINT FK_723705D1A76ED395 FOREIGN KEY (user_id) REFERENCES personne (id_pers)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE transaction DROP FOREIGN KEY FK_723705D1A76ED395');
        $this->addSql('DROP TABLE transaction');
    }
}
