<?php

namespace App\Command;

use App\Entity\Personne;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Console\Attribute\AsCommand;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputArgument;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Input\InputOption;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\Console\Style\SymfonyStyle;

#[AsCommand(
    name: 'app:deleteuser',
    description: 'delete when inactive',
)]
class DeleteuserCommand extends Command
{
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;

        parent::__construct();
    }
    protected function configure(): void
    {
        $this
            ->addArgument('arg1', InputArgument::OPTIONAL, 'Argument description')
            ->addOption('option1', null, InputOption::VALUE_NONE, 'Option description')
        ;
    }

    protected function execute(InputInterface $input, OutputInterface $output): int
    {
        $reclams = $this->entityManager->getRepository(Personne::class)->findAll();
        $avant = time() - 2592000; 
        $nbReclamSupprime = 0;
        foreach ($reclams as $reclam){
            if($reclam->getDate()->getTimestamp() < $avant ){
                $this->entityManager->getRepository(Personne::class)->remove($reclam,true);
                $nbReclamSupprime += 1;
            }
           
        }
        $output->writeln("[Script] Nb d'utilisateur inactif Supprimé : ".$nbReclamSupprime);
        return Command::SUCCESS;
    }
}
