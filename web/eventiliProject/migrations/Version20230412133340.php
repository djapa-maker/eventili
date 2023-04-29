<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230412133340 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE sponsoring (id_sponso INT AUTO_INCREMENT NOT NULL, id_trans INT NOT NULL, date_debut DATE NOT NULL, date_fin DATE NOT NULL, nombre_impression INT NOT NULL, UNIQUE INDEX UNIQ_E459A50E9EA45F8D (id_trans), PRIMARY KEY(id_sponso)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE sponsoring ADD CONSTRAINT FK_E459A50E9EA45F8D FOREIGN KEY (id_trans) REFERENCES transaction (id_trans)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE sponsoring DROP FOREIGN KEY FK_E459A50E9EA45F8D');
        $this->addSql('DROP TABLE sponsoring');
    }
}
