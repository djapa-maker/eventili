<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230412161805 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE sponsoring DROP INDEX UNIQ_E459A50E9EA45F8D, ADD INDEX IDX_E459A50E9EA45F8D (id_trans)');
        $this->addSql('ALTER TABLE sponsoring ADD id_event INT NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE sponsoring DROP INDEX IDX_E459A50E9EA45F8D, ADD UNIQUE INDEX UNIQ_E459A50E9EA45F8D (id_trans)');
        $this->addSql('ALTER TABLE sponsoring DROP id_event');
    }
}
