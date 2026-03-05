function makeConfig(serviceName) {
    return {
        branches: ['main'],
        tagFormat: `${serviceName}-v\${version}`,
        plugins: [
            [
                '@semantic-release/commit-analyzer',
                {
                    preset: 'conventionalcommits',
                    releaseRules: [
                        {scope: serviceName, type: 'feat', release: 'minor'},
                        {scope: serviceName, type: 'fix', release: 'patch'},
                        {scope: serviceName, type: 'perf', release: 'patch'},
                        {scope: serviceName, breaking: true, release: 'major'},
                        {release: false},
                    ],
                    parserOpts: {
                        noteKeywords: ['BREAKING CHANGE', 'BREAKING CHANGES'],
                    },
                },
            ],
            [
                '@semantic-release/release-notes-generator',
                {
                    preset: 'conventionalcommits',
                    parserOpts: {
                        noteKeywords: ['BREAKING CHANGE', 'BREAKING CHANGES'],
                    },
                    writerOpts: {
                        transform: (commit, context) => {
                            if (commit.scope && commit.scope !== serviceName) return false;
                            return commit;
                        },
                    },
                },
            ],
            [
                '@semantic-release/changelog',
                {
                    changelogFile: `${serviceName}/CHANGELOG.md`,
                },
            ],
            [
                '@semantic-release/exec',
                {
                    prepareCmd: `sed -i 's/^version = ".*"/version = "\${nextRelease.version}"/' ${serviceName}/build.gradle.kts`,
                },
            ],
            [
                '@semantic-release/git',
                {
                    assets: [
                        `${serviceName}/CHANGELOG.md`,
                        `${serviceName}/build.gradle.kts`,
                    ],
                    message: `chore(release): ${serviceName}-v\${nextRelease.version} [skip ci]\n\n\${nextRelease.notes}`,
                },
            ],
            '@semantic-release/github',
        ],
    };
}

module.exports = {makeConfig};