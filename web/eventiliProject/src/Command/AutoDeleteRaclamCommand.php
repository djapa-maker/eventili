<?php

namespace App\Command;

use App\Entity\Reclamation;
use App\Entity\Reponse;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Console\Attribute\AsCommand;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputArgument;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Input\InputOption;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\Console\Style\SymfonyStyle;

#[AsCommand(
    name: 'app:supprimer:reclam',
    description: 'Supprime les réclamations agé de plus de 30 J',
)]
class AutoDeleteRaclamCommand extends Command
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
        $reclams = $this->entityManager->getRepository(Reclamation::class)->findAll();
        $avant = time() - 2592000;
        $nbReclamSupprime = 0;
        foreach ($reclams as $reclam){
            if($reclam->getDateheure()->getTimestamp() < $avant && $reclam->getStatus() == "cloturer"){
                $this->entityManager->getRepository(Reclamation::class)->remove($reclam,true);
                $nbReclamSupprime += 1;
            }
            $lastRes = $this->entityManager->getRepository(Reponse::class)->getLast($reclam);
            if(!empty($lastRes)){
                if($lastRes->getTimestamp()->getTimestamp() < $avant){
                    $this->entityManager->getRepository(Reclamation::class)->remove($reclam,true);
                    $nbReclamSupprime += 1;
                }
            }
            if(empty($lastRes) && $reclam->getDateheure()->getTimestamp() < $avant){
                $this->entityManager->getRepository(Reclamation::class)->remove($reclam,true);
                $nbReclamSupprime += 1;
            }
        }
        $output->writeln("[Script] Nb Reclamation Cloturé Supprimer : ".$nbReclamSupprime);
        return Command::SUCCESS;
    }
}
